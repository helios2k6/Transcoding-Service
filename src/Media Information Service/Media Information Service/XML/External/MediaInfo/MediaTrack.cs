
using YAXLib;
namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents a Media type track
	/// </summary>
	public abstract class MediaTrack : Track
	{
		/// <summary>
		/// The ID XML node
		/// </summary>
		[YAXSerializeAs(XmlConstants.Id)]
		public string Id { get; set; }
	}
}
