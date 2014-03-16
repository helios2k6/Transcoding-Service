using System;
using System.Linq;
using MediaInformationService.Thirdparty;
using MediaInformationService.XML.Response;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using YAXLib;

namespace Unit_Tests
{
	[TestClass]
	public sealed class ResponseDeserializationTests
	{
		[TestMethod]
		public void SerializeResponseYAX()
		{

		}

		[TestMethod]
		public void DeserializeResponseYAX()
		{
			var deserializer = new YAXSerializer(typeof(MediaInformationServicesResponse));
			MediaInformationServicesResponse actualResponse;
			if (deserializer.TryDeserialize(XmlStrings.ValidXMLResponse, out actualResponse))
			{
				Assert.AreEqual(2, actualResponse.Files.Count());

				var firstPath = actualResponse.Files.ElementAt(0);
				var secondPath = actualResponse.Files.ElementAt(1);

				Assert.IsTrue(XmlStrings.ValidXMLExpectedPath1.Equals(firstPath.Path, StringComparison.OrdinalIgnoreCase));
				Assert.IsTrue(XmlStrings.ValidXMLExpectedPath2.Equals(secondPath.Path, StringComparison.OrdinalIgnoreCase));

				Assert.IsNull(secondPath.MediaInfo);
			}
			else
			{
				Assert.Fail();
			}
		}
	}
}
