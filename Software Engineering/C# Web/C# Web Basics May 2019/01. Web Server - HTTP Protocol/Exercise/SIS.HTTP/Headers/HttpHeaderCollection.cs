﻿using SIS.HTTP.Common;
using System.Collections.Generic;
using System.Linq;

namespace SIS.HTTP.Headers
{
    class HttpHeaderCollection : IHttpHeaderCollection
    {
        private readonly Dictionary<string, HttpHeader> headers;

        public HttpHeaderCollection()
        {
            headers = new Dictionary<string, HttpHeader>();
        }

        public void AddHeader(HttpHeader header)
        {
            CoreValidator.ThrowIfNull(header, nameof(header));
            headers.Add(header.Key, header);
        }

        public bool ContainsHeader(string key)
        {
            CoreValidator.ThrowIfNullOrEmpty(key, nameof(key));
            return headers.ContainsKey(key);
        }

        public HttpHeader GetHeader(string key)
        {
            CoreValidator.ThrowIfNullOrEmpty(key, nameof(key));

            // TODO: Validation for existing parameter

            return headers[key];
        }

        public override string ToString() => string.Join("\r\n",
            headers.Values.Select(header => header.ToString()));
    }
}
