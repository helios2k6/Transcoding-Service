using YAXLib;

namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents the Track XML node
	/// </summary>
	[YAXCustomSerializer(typeof(TrackSerializer))]
	[YAXSerializableType(Options = YAXSerializationOptions.SerializeNullObjects)]
	public abstract class Track
	{
		/// <summary>
		/// The type XML attribute
		/// </summary>
		[YAXSerializeAs(XmlConstants.Type)]
		[YAXAttributeForClass]
		public string Type { get; set; }

		/// <summary>
		/// The Format XML node
		/// </summary>
		public string Format { get; set; }
	}
}