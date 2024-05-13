
using System.Xml.Serialization;

namespace Lab_9
{
    [XmlRoot(ElementName = "Engine")]
    public class Engine
    {
        public double displacement { get; set; }
        public double horsePower { get; set; }
        [XmlAttribute]
        public string model { get; set; }

        public Engine()
        {

        }

        public Engine(double Displacement, double HorsePower, string Model)
        {
            displacement = Displacement;
            horsePower = HorsePower;
            model = Model;
        }

    }
}