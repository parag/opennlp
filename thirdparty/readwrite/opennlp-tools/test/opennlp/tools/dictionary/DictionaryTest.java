///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2007 OpenNlp
// 
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
// 
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Lesser General Public License for more details.
// 
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package opennlp.tools.dictionary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import opennlp.tools.dictionary.Dictionary;
import opennlp.tools.ngram.Token;
import opennlp.tools.ngram.TokenList;
import opennlp.tools.util.InvalidFormatException;

import junit.framework.TestCase;

/**
  * Tests for the {@link Dictionary} class.
  */
public class DictionaryTest extends TestCase  {
  
  /**
   * Tests a basic lookup.
   */
  public void testLookup() {
    
    TokenList entry1 = Token.create(new String[]{"1a", "1b"});
    TokenList entry2 = Token.create(new String[]{"1A", "1C"});
    
    Dictionary dict = new Dictionary();

    dict.put(entry1);
    
    assertTrue(dict.contains(entry1));
    assertTrue(!dict.contains(entry2));
  }
  
  /**
   * Tests serialization and deserailization of the {@link Dictionary}.
   * 
   * @throws IOException
   * @throws InvalidFormatException 
   */
  public void testSerialization() throws IOException, InvalidFormatException {
    Dictionary reference = new Dictionary();
    
    Token a1 = Token.create("a1");
    Token a2 = Token.create("a2");
    Token a3 = Token.create("a3");
    Token a5 = Token.create("a5");
    
    reference.put(new TokenList(new Token[]{a1, a2, a3, a5,}));
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    reference.serialize(out);
  
    Dictionary recreated = new Dictionary(
        new ByteArrayInputStream(out.toByteArray()));
    
    assertTrue(reference.equals(recreated));
  }
  
  /**
   * Tests for the {@link Dictionary#parseOneEntryPerLine(java.io.Reader)} 
   * method.
   * 
   * @throws IOException 
   */
  public void testParseOneEntryPerLine() throws IOException {
  
    String testDictionary = "1a 1b 1c 1d \n 2a 2b 2c \n 3a \n 4a    4b   ";
  
    Dictionary dictionay = 
      Dictionary.parseOneEntryPerLine(new StringReader(testDictionary));
    
    assertTrue(dictionay.size() == 4);
    
    assertTrue(dictionay.contains(
        Token.create(new String[]{"1a", "1b", "1c", "1d"})));

    assertTrue(dictionay.contains(
        Token.create(new String[]{"2a", "2b", "2c"})));

    assertTrue(dictionay.contains(
        Token.create(new String[]{"3a"})));

    assertTrue(dictionay.contains(
        Token.create(new String[]{"4a", "4b"})));
  }
  
  /**
   * Tests for the {@link Dictionary#equals(Object)} method.
   */
  public void testEquals() {
    TokenList entry1 = Token.create(new String[]{"1a", "1b"});
    TokenList entry2 = Token.create(new String[]{"2a", "2b"});
    
    Dictionary dictA = new Dictionary();
    dictA.put(entry1);
    dictA.put(entry2);
    
    Dictionary dictB = new Dictionary();
    dictB.put(entry1);
    dictB.put(entry2);
    
    assertTrue(dictA.equals(dictB));
  }
  
  /**
   * Tests the {@link Dictionary#hashCode()} method.
   */
  public void testHashCode() {
    TokenList entry1 = Token.create(new String[]{"1a", "1b"});
    
    Dictionary dictA = new Dictionary();
    dictA.put(entry1);

    Dictionary dictB = new Dictionary();
    dictB.put(entry1);

    assertEquals(dictA.hashCode(), dictB.hashCode());
  }
  
  /**
   * Tests for the {@link Dictionary#toString()} method.
   */
  public void testToString() {
    TokenList entry1 = Token.create(new String[]{"1a", "1b"});
    
    Dictionary dictA = new Dictionary();

    dictA.toString();

    dictA.put(entry1);
    
    dictA.toString();
  }
  
  /**
   * Tests the lookup of tokens of different case.
   */
  public void testDifferentCaseLookup() {
    
    TokenList entry1 = Token.create(new String[]{"1a", "1b"});
    TokenList entry2 = Token.create(new String[]{"1A", "1B"});
    
    Dictionary dict = new Dictionary();

    dict.put(entry1);
    
    assertTrue(dict.contains(entry2));
  }
}