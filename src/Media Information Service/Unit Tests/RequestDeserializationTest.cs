using System;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.Xml;
using MediaInformationService.Thirdparty;
using MediaInformationService.XML.Internal.Request;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using YAXLib;

namespace Unit_Tests
{
	/// <summary>
	/// Summary description for RequestDeserializationTest
	/// </summary>
	[TestClass]
	public class RequestDeserializationTest
	{
		[TestMethod]
		public void DeserializeRequest()
		{
			var deserializer = new DataContractSerializer(typeof(MediaInformationServicesRequest));
			using (var stringReader = new StringReader(XmlStrings.ValidXmlRequest))
			{
				using (var xmlReader = new XmlTextReader(stringReader))
				{
					var actualRequest = (MediaInformationServicesRequest)deserializer.ReadObject(xmlReader);

					Assert.AreEqual(2, actualRequest.Files.Count());

					var firstPath = actualRequest.Files.ElementAt(0);
					var secondPath = actualRequest.Files.ElementAt(1);

					Assert.IsTrue(XmlStrings.ValidXmlExpectedPath1.Equals(firstPath.Path, StringComparison.OrdinalIgnoreCase));
					Assert.IsTrue(XmlStrings.ValidXmlExpectedPath2.Equals(secondPath.Path, StringComparison.OrdinalIgnoreCase));
				}
			}
		}

		[TestMethod]
		public void DeserializeRequestYAX()
		{
			var deserializer = new YAXSerializer(typeof(MediaInformationServicesRequest));
			MediaInformationServicesRequest actualRequest;
			if (deserializer.TryDeserialize(XmlStrings.ValidXmlRequest, out actualRequest))
			{
				Assert.AreEqual(2, actualRequest.Files.Count());

				var firstPath = actualRequest.Files.ElementAt(0);
				var secondPath = actualRequest.Files.ElementAt(1);

				Assert.IsTrue(XmlStrings.ValidXmlExpectedPath1.Equals(firstPath.Path, StringComparison.OrdinalIgnoreCase));
				Assert.IsTrue(XmlStrings.ValidXmlExpectedPath2.Equals(secondPath.Path, StringComparison.OrdinalIgnoreCase));
			}
			else
			{
				Assert.Fail();
			}
		}
	}
}
