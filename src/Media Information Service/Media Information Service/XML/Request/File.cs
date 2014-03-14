using System;
using System.Security.Policy;
using YAXLib;

namespace MediaInformationService.XML.Request
{
	/// <summary>
	/// Represents the File node of a Media Information Service Request
	/// </summary>
	[YAXSerializeAs("File")]
	[Serializable]
	public sealed class File
	{
		/// <summary>
		/// Get or set the Path of the File XML node
		/// </summary>
		[YAXSerializeAs("Path")]
		public Url Path { get; set; }
	}
}