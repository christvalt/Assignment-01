/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a simple text extraction example to get started. For more advance usage, see the
 * ExtractTextByArea and the DrawPrintTextLocations examples in this subproject, as well as the
 * ExtractText tool in the tools subproject.
 *
 * @author Tilman Hausherr
 */
public class ExtractTextSimple {

    public ExtractTextSimple() {

    }

    public static void textExtractor() throws IOException {

        final  File DIRECTORY = new File("C:\\Users\\camerum\\Desktop\\SW\\");

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith(".pdf");
            }
        };
        File[] files = DIRECTORY.listFiles(filter);
        List<String> AllTheText = new ArrayList<String>();

            for (int j = 0; j < files.length; j++) {
                PDDocument document = PDDocument.load(files[j]);
                System.out.println(document);
                AccessPermission ap = document.getCurrentAccessPermission();
                if (!ap.canExtractContent()) {
                    throw new IOException("You do not have permission to extract text");
                }
                PDFTextStripper stripper = new PDFTextStripper();
                String ExtractedText = "";
                stripper.setSortByPosition(true);
                for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                    // Set the page interval to extract. If you don't, then all pages would be extracted.
                    stripper.setStartPage(p);
                    stripper.setEndPage(p);
                    // let the magic happen
                      String text = stripper.getText(document);
                    ExtractedText = ExtractedText + text;
                }
                document.close();
                AllTheText.add(ExtractedText);

            }
        System.out.println(AllTheText.get(0));
        System.out.println("my size is "+ AllTheText.size());


    }
}
