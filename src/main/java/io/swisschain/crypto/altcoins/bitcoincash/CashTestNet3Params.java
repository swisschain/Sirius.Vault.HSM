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

import org.bitcoinjcash.params.TestNet3Params;

/**
 * Parameters for the testnet, a separate public instance of Bitcoin that has relaxed rules suitable
 * for development and testing of applications and new Bitcoin versions.
 */
public class CashTestNet3Params extends TestNet3Params {
  private static CashTestNet3Params instance;

  public CashTestNet3Params() {
    super();
    packetMagic = 0xf4f3e5f4;
    cashAddrPrefix = "bchtest";
  }

  public static synchronized CashTestNet3Params get() {
    if (instance == null) {
      instance = new CashTestNet3Params();
    }
    return instance;
  }
}
