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
package opennlp.tools.namefind;

import junit.framework.TestCase;
import opennlp.maxent.GISModel;
import opennlp.maxent.io.BinaryGISModelReader;

import java.io.DataInputStream;
import java.util.zip.GZIPInputStream;

public class MultiThreadTest extends TestCase {
  
  private GISModel locationModel;
  
  public void setUp() throws Exception
  {
    BinaryGISModelReader modelReader = new BinaryGISModelReader(new DataInputStream(new GZIPInputStream(this.getClass().getResourceAsStream("test.bin.gz"))));
    locationModel = modelReader.getModel();
  }

  public void testNameFinderModelThreadSafety()
  {
    int tc=100;
    final java.util.Random r = new java.util.Random();
    Thread[] threads = new Thread[tc];
    for (int ti=0;ti<tc;ti++) {
      threads[ti] = new Thread() {

        public void run() {
          String[] content1 = {"John", "works", "for", "the", "Department", "of",
              "the", "Navy", "in", "Washington", "DC", "."};
          NameFinderME finder = new NameFinderME(locationModel);
          try {
            sleep(r.nextInt(10));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          finder.find(content1);
        }
      };
    }
    for (int i=0;i<1;i++) {
      for (int ti=0;ti<tc;ti++) {
        threads[ti].start();
      }
    }
  }
  
  

}
