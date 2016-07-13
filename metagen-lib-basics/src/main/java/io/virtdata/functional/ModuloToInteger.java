/*
 *
 *       Copyright 2015 Jonathan Shook
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package io.virtdata.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.LongToIntFunction;

public class ModuloToInteger implements LongToIntFunction {
    private final static Logger logger = LoggerFactory.getLogger(ModuloToInteger.class);

    private final int modulo;

    public ModuloToInteger(int modulo) {
        this.modulo=modulo;
    }
    public ModuloToInteger(String modulo) {
        this(Integer.valueOf(modulo));
    }

    @Override
    public int applyAsInt(long operand) {
        int ret = (int) (operand % modulo) & Integer.MAX_VALUE;
        return ret;
    }
}