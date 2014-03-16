using YAXLib;

namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// An Audio type media track
	/// </summary>
	public sealed class AudioTrack : MediaTrack
	{
		/// <summary>
		/// The Channel_s_ XML node
		/// </summary>
		[YAXSerializeAs(XmlConstants.Channels)]
		public string Channels { get; set; }

		/// <summary>
		/// The Language XML node
		/// </summary>
		public string Language { get; set; }
	}
}
