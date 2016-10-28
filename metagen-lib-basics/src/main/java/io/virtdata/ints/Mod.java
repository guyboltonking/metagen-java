/*
*   Copyright 2016 jshook
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package io.virtdata.ints;

import java.util.function.IntUnaryOperator;

public class Mod implements IntUnaryOperator {
    private int modulo;

    public Mod(int modulo) {
        this.modulo = modulo;
    }

    @Override
    public int applyAsInt(int operand) {
        return operand % modulo;
    }
}