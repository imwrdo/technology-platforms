
using System.Xml.Serialization;

namespace Lab_9
{
    [XmlType("car")]           
    public class Car
    {
        public string model;
        public int year;
        [XmlElement(ElementName = "Engine")]
        public Engine motor { get; set; } 

        public Car()
        {

        }

        public Car(string modelName, Engine motorOnBoard, int yearOfProduction)
        {
            model = modelName;
            year = yearOfProduction;
            motor = motorOnBoard;
        }

        public void setModel(string modelName)
        {
            model = modelName;
        }

        public string getModel()
        {
            return model;
        }

        public void setYear(int yearOfProduction)
        {
            year = yearOfProduction;
        }

        public int getYear()
        {
            return year;
        }
    }
}