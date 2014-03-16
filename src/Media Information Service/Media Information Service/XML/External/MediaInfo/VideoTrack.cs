using YAXLib;

namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents a Video type media track
	/// </summary>
	public sealed class VideoTrack : MediaTrack
	{
		/// <summary>
		/// The Width XML node
		/// </summary>
		public string Width { get; set; }

		/// <summary>
		/// The Height XML node
		/// </summary>
		public string Height { get; set; }

		/// <summary>
		/// The DisplayAspectRatio XML node
		/// </summary>
		[YAXSerializeAs(XmlConstants.DisplayAspectRatio)]
		public string DisplayAspectRatio { get; set; }

		/// <summary>
		/// The Frame_rate XML node
		/// </summary>
		[YAXSerializeAs(XmlConstants.FrameRate)]
		public string FrameRate { get; set; }
	}
}
