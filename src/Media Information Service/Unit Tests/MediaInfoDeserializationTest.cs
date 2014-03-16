using Microsoft.VisualStudio.TestTools.UnitTesting;
using YAXLib;
using MediaInformationService.XML.External.MediaInfo;
using MediaInformationService.Thirdparty;

namespace Unit_Tests
{
	[TestClass]
	public class MediaInfoDeserializationTest
	{
		[TestMethod]
		public void DeserializeMediaInfo()
		{
			var deserializer = new YAXSerializer(typeof(GeneralTrack));
			MediaInfo mediaInfo;
			if (deserializer.TryDeserialize(XmlStrings.ValidMediaInfoXml, out mediaInfo))
			{
				File file = mediaInfo.File;
				Assert.AreEqual(4, file.Tracks.Length);

				
			}
			else
			{
				Assert.Fail();
			}
		}
	}
}
