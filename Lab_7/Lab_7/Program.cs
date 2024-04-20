using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace lab_7
{
    public static class FileSystemExtensions
    {
        public static DateTime GetOldestFile(this DirectoryInfo directoryInfo)
        {
            DateTime oldestDateTime = DateTime.Now;

            foreach (var subDirectory in directoryInfo.GetDirectories())
            {
                DateTime subDirectoryOldestDateTime = subDirectory.GetOldestFile();
                if (subDirectoryOldestDateTime < oldestDateTime)
                {
                    oldestDateTime = subDirectoryOldestDateTime;
                }
            }

            foreach (var file in directoryInfo.GetFiles())
            {
                DateTime fileDateTime = file.CreationTime;
                if (fileDateTime < oldestDateTime)
                {
                    oldestDateTime = fileDateTime;
                }
            }

            return oldestDateTime;
        }

        public static string GetDOSAttributes(this FileSystemInfo fileSystemInfo)
        {
            FileAttributes attributes = fileSystemInfo.Attributes;
            return $"{(attributes.HasFlag(FileAttributes.ReadOnly) ? 'r' : '-')}" +
                   $"{(attributes.HasFlag(FileAttributes.Archive) ? 'a' : '-')}" +
                   $"{(attributes.HasFlag(FileAttributes.Hidden) ? 'h' : '-')}" +
                   $"{(attributes.HasFlag(FileAttributes.System) ? 's' : '-')}";
        }
    }

    [Serializable]
    public class StringLengthComparer : IComparer<string>
    {
        public int Compare(string x, string y)
        {
            int lengthComparison = x.Length.CompareTo(y.Length);
            if (lengthComparison != 0)
            {
                return lengthComparison;
            }
            return string.Compare(x, y, StringComparison.Ordinal);
        }
    }

    internal class Program
    {
        private static void SerializeData<T>(T data, string filePath)
        {
            BinaryFormatter formatter = new BinaryFormatter();
            using (FileStream fileStream = new FileStream(filePath, FileMode.Create))
            {
                formatter.Serialize(fileStream, data);
            }
        }

        private static T DeserializeData<T>(string filePath)
        {
            BinaryFormatter formatter = new BinaryFormatter();
            using (var fileStream = new FileStream(filePath, FileMode.Open))
            {
                return (T)formatter.Deserialize(fileStream);
            }
        }

        private static void ListAllFilesIndented(DirectoryInfo directoryInfo, int indentationLevel)
        {
            foreach (var info in directoryInfo.GetFileSystemInfos())
            {
                string indentation = new string(' ', indentationLevel * 2);

                if (info is FileInfo fileInfo)
                {
                    Console.WriteLine($"{indentation}{fileInfo.Name} (Size: {fileInfo.Length} bytes) {fileInfo.GetDOSAttributes()}");
                }
                else if (info is DirectoryInfo subDirectoryInfo)
                {
                    Console.WriteLine($"{indentation}{subDirectoryInfo.Name} (Contains: {subDirectoryInfo.GetFileSystemInfos().Length} items) {subDirectoryInfo.GetDOSAttributes()}");
                    ListAllFilesIndented(subDirectoryInfo, indentationLevel + 1);
                }
            }
        }

        private static void LoadDirectoryData(string directoryPath)
        {
            DirectoryInfo directoryInfo = new DirectoryInfo(directoryPath);
            var items = new SortedDictionary<string, long>(new StringLengthComparer());

            foreach (var info in directoryInfo.GetFileSystemInfos())
            {
                if (info is FileInfo fileInfo)
                {
                    items[fileInfo.Name] = fileInfo.Length;
                }
                else if (info is DirectoryInfo subDirectoryInfo)
                {
                    items[subDirectoryInfo.Name] = subDirectoryInfo.GetFileSystemInfos().Length;
                }
            }

            string serializedFilePath = Path.Combine(directoryPath, "test.txt");
            SerializeData(items, serializedFilePath);

            var deserializedItems = DeserializeData<SortedDictionary<string, long>>(serializedFilePath);

            Console.WriteLine("Results:");

            foreach (var item in deserializedItems)
            {
                Console.WriteLine($"{item.Key} ----- {item.Value}");
            }
        }

        private static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("No directory specified.");
                return;
            }

            string directoryPath = args[0];

            if (!Directory.Exists(directoryPath))
            {
                Console.WriteLine($"Directory '{directoryPath}' does not exist.");
                return;
            }

            DirectoryInfo directoryInfo = new DirectoryInfo(directoryPath);
            Console.WriteLine("Contents of the directory: " + directoryPath);

            try
            {
                ListAllFilesIndented(directoryInfo, 0);
                Console.WriteLine("The oldest file in the directory: " + directoryInfo.GetOldestFile());
                LoadDirectoryData(directoryPath);
            }
            catch (Exception e)
            {
                Console.WriteLine($"An error occurred: {e.Message}");
            }
        }
    }
}
