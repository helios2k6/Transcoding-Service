using System;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// The AudioTrack XML node
	/// </summary>
	[Serializable]
	public sealed class AudioTrack : Track
	{
		/// <summary>
		/// The Channels XML node
		/// </summary>
		[YAXSerializeAs("Channels")]
		public int Channels { get; set; }

		/// <summary>
		/// The Language XML node
		/// </summary>
		[YAXSerializeAs("Language")]
		public string Language { get; set; }
	}
}