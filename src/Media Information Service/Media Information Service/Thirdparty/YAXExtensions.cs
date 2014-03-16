using System.Diagnostics.Contracts;
using YAXLib;
namespace MediaInformationService.Thirdparty
{
	public static class YAXExtensions
	{
		/// <summary>
		/// Deserializes an object T from the contents given
		/// </summary>
		/// <typeparam name="T">The type to be deserialized</typeparam>
		/// <param name="serializer">The serializer object</param>
		/// <param name="contents">The string representation of the object</param>
		/// <returns>A newly deserialized T or null</returns>
		public static T Deserialize<T>(this YAXSerializer serializer, string contents)
		{
			Contract.Requires(serializer != null);
			Contract.Requires(contents != null);

			T t;
			if(serializer.TryDeserialize(contents, out t))
			{
				return t;
			}

			return t;
		}

		/// <summary>
		/// Attempts to deserialize the contents, passing back a boolean value 
		/// </summary>
		/// <typeparam name="T">The type to be deserialized</typeparam>
		/// <param name="serializer">The serializer</param>
		/// <param name="contents">The contents</param>
		/// <param name="t">The reference to assign the deserialized object to</param>
		/// <returns>True upon successful deserialization. False otherwise</returns>
		public static bool TryDeserialize<T>(this YAXSerializer serializer, string contents, out T t)
		{
			Contract.Requires(serializer != null);
			Contract.Requires(contents != null);

			object o = serializer.Deserialize(contents);
			if (o != null && o.GetType() == typeof(T))
			{
				t = (T)o;
				return true;
			}

			t = default(T);
			return false;
		}
	}
}
