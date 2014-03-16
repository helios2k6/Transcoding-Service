using YAXLib;

namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents the MediaInfo XML node
	/// </summary>
	[YAXSerializeAs(XmlConstants.MediaInfo)]
	public sealed class MediaInfo
	{
		public File File { get; set; }
	}
}
