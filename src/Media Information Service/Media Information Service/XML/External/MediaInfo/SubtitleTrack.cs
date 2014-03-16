namespace MediaInformationService.XML.External.MediaInfo
{
	/// <summary>
	/// Represents a Subtitle type media track
	/// </summary>
	public sealed class SubtitleTrack : MediaTrack
	{
		/// <summary>
		/// The Language XML node
		/// </summary>
		public string Language { get; set; }
	}
}
