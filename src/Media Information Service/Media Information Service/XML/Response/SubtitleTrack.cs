using System;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The SubtitleTrack XML node
	/// </summary>
	[Serializable]
	public sealed class SubtitleTrack : Track
	{
		/// <summary>
		/// The Language XML node
		/// </summary>
		[YAXSerializeAs("Language")]
		public string Language { get; set; }
	}
}