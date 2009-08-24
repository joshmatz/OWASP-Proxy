/*
 *  This file is part of the OWASP Proxy, a free intercepting HTTP proxy
 *  library.
 *  Copyright (C) 2008  Rogan Dawes <rogan@dawes.za.net>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as 
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
package org.owasp.proxy.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.owasp.proxy.httpclient.MessageFormatException;
import org.owasp.proxy.httpclient.NamedValue;

public class NamedValueTest {

	@Test
	public void testParse() throws MessageFormatException {
		String t;
		NamedValue nv;
		t = "a=b";
		nv = NamedValue.parse(t, "=");
		assertEquals("a", nv.getName());
		assertEquals("=", nv.getSeparator());
		assertEquals("b", nv.getValue());
		assertEquals(t, nv.toString());

		t = "a=";
		nv = NamedValue.parse(t, "=");
		assertEquals("a", nv.getName());
		assertEquals("=", nv.getSeparator());
		assertEquals("", nv.getValue());
		assertEquals(t, nv.toString());

		t = "a: b";
		nv = NamedValue.parse(t, " *: *");
		assertEquals("a", nv.getName());
		assertEquals(": ", nv.getSeparator());
		assertEquals("b", nv.getValue());
		assertEquals(t, nv.toString());

		t = "a:";
		nv = NamedValue.parse(t, " *: *");
		assertEquals("a", nv.getName());
		assertEquals(":", nv.getSeparator());
		assertEquals("", nv.getValue());
		assertEquals(t, nv.toString());

		t = "a : b";
		nv = NamedValue.parse(t, " *: *");
		assertEquals("a", nv.getName());
		assertEquals(" : ", nv.getSeparator());
		assertEquals("b", nv.getValue());
		assertEquals(t, nv.toString());
	}

	@Test
	public void testJoin() throws MessageFormatException {
		String t = "a=b&c=d&e=&f=g";
		NamedValue[] nv = NamedValue.parse(t, "&", "=");
		assertEquals(4, nv.length);

		assertEquals("a", nv[0].getName());
		assertEquals("=", nv[0].getSeparator());
		assertEquals("b", nv[0].getValue());

		assertEquals("c", nv[1].getName());
		assertEquals("=", nv[1].getSeparator());
		assertEquals("d", nv[1].getValue());

		assertEquals("e", nv[2].getName());
		assertEquals("=", nv[2].getSeparator());
		assertEquals("", nv[2].getValue());

		assertEquals("f", nv[3].getName());
		assertEquals("=", nv[3].getSeparator());
		assertEquals("g", nv[3].getValue());

		assertEquals(t, NamedValue.join(nv, "&"));
	}

}
