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

import org.bitcoinjcash.params.MainNetParams;

/** Parameters for the main production network on which people trade goods and services. */
public class CashMainNetParams extends MainNetParams {
  private static CashMainNetParams instance;

  public CashMainNetParams() {
    super();
    packetMagic = 0xe8f3e1e3;
    cashAddrPrefix = "bitcoincash";
  }

  public static synchronized CashMainNetParams get() {
    if (instance == null) {
      instance = new CashMainNetParams();
    }
    return instance;
  }
}
