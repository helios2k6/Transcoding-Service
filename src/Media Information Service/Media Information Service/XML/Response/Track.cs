using System;
using YAXLib;

namespace MediaInformationService.XML.Response
{
	/// <summary>
	/// Base class for all Track XML nodes
	/// </summary>
	[Serializable]
	public abstract class Track
	{
		/// <summary>
		/// The Track ID (track number)
		/// </summary>
		[YAXSerializeAs("Id")]
		public int Id { get; set; }

		/// <summary>
		/// The Format XML node
		/// </summary>
		[YAXSerializeAs("Format")]
		public string Format { get; set;}
	}
}