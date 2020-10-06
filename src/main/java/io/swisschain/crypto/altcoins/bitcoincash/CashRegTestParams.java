/*
 * Copyright 2013 Google Inc.
 * Copyright 2018 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.swisschain.crypto.altcoins.bitcoincash;

import org.bitcoinjcash.params.RegTestParams;

/**
 * Network parameters for the regression test mode of bitcoind in which all blocks are trivially
 * solvable.
 */
public class CashRegTestParams extends RegTestParams {
  private static CashRegTestParams instance;

  public CashRegTestParams() {
    super();
    cashAddrPrefix = "bchreg";
  }

  public static synchronized CashRegTestParams get() {
    if (instance == null) {
      instance = new CashRegTestParams();
    }
    return instance;
  }
}
