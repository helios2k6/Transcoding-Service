using System;
using System.Linq;
using MediaInformationService.Thirdparty;
using MediaInformationService.XML.Internal.Response;
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
			var deserializer = new YAXSerializer(typeof(MediaInformationServicesResponse), YAXExceptionHandlingPolicies.DoNotThrow);
			MediaInformationServicesResponse actualResponse;
			if (deserializer.TryDeserialize(XmlStrings.ValidXmlResponse, out actualResponse))
			{
				Assert.AreEqual(2, actualResponse.Files.Count());

				var firstPath = actualResponse.Files.ElementAt(0);
				var secondPath = actualResponse.Files.ElementAt(1);

				Assert.IsTrue(XmlStrings.ValidXmlExpectedPath1.Equals(firstPath.Path, StringComparison.OrdinalIgnoreCase));
				Assert.IsTrue(XmlStrings.ValidXmlExpectedPath2.Equals(secondPath.Path, StringComparison.OrdinalIgnoreCase));

				Assert.IsNull(secondPath.MediaInfo);
			}
			else
			{
				Assert.Fail();
			}
		}
	}
}
