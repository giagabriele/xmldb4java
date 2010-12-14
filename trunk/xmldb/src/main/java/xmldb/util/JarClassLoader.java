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
package xmldb.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author Giacomo Stefamo Gabriele
 */
public class JarClassLoader extends ClassLoader{

   private final ZipFile file;

    public JarClassLoader(String filename) throws IOException {
        this.file = new ZipFile(filename);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ZipEntry entry = this.file.getEntry(name.replace('.', '/') + ".class");
        if (entry == null) {
            throw new ClassNotFoundException(name);
        }
        try {
            byte[] array = new byte[1024];
            InputStream in = this.file.getInputStream(entry);
            ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
            int length = in.read(array);
            while (length > 0) {
                out.write(array, 0, length);
                length = in.read(array);
            }
            return defineClass(name, out.toByteArray(), 0, out.size());
        }
        catch (IOException exception) {
            throw new ClassNotFoundException(name, exception);
        }
    }



}
