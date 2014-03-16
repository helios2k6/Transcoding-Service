using System;
using System.Runtime.Serialization;

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
		[DataMember(Name = XmlConstants.Id, IsRequired = true)]
		public int Id { get; set; }

		/// <summary>
		/// The Format XML node
		/// </summary>
		[DataMember(Name = XmlConstants.Format, IsRequired = true)]
		public string Format { get; set; }
	}
}