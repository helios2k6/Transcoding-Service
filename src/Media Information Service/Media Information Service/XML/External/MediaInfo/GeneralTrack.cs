using YAXLib;

namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents a General track type
	/// </summary>
	public sealed class GeneralTrack : Track
	{
		/// <summary>
		/// The Unique_ID XML node
		/// </summary>
		[YAXSerializeAs(XmlConstants.UniqueId)]
		public string UniqueId { get; set; }

		/// <summary>
		/// The Duration XML node
		/// </summary>
		public string Duration { get; set; }
	}
}
