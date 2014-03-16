using YAXLib;
namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents the File XML node
	/// </summary>
	public sealed class File
	{
		[YAXCollection(YAXCollectionSerializationTypes.RecursiveWithNoContainingElement, EachElementName = XmlConstants.Track)]
		public Track[] Tracks { get; set; }
	}
}
