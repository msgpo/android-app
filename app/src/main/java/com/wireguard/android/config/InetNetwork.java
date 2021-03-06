/*
 * Copyright © 2018 Samuel Holland <samuel@sholland.org>
 * Copyright © 2018 Jason A. Donenfeld <Jason@zx2c4.com>. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.wireguard.android.config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Objects;

public class InetNetwork {
    private final InetAddress address;
    private final int mask;

    public InetNetwork(final String input) {
        final int slash = input.lastIndexOf('/');
        final int rawMask;
        final String rawAddress;
        if (slash >= 0) {
            rawMask = Integer.parseInt(input.substring(slash + 1), 10);
            rawAddress = input.substring(0, slash);
        } else {
            rawMask = -1;
            rawAddress = input;
        }
        address = InetAddresses.parse(rawAddress);
        final int maxMask = (address instanceof Inet4Address) ? 32 : 128;
        mask = rawMask >= 0 && rawMask <= maxMask ? rawMask : maxMask;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof InetNetwork))
            return false;
        final InetNetwork other = (InetNetwork) obj;
        return Objects.equals(address, other.address) && mask == other.mask;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getMask() {
        return mask;
    }

    @Override
    public int hashCode() {
        return address.hashCode() ^ mask;
    }

    @Override
    public String toString() {
        return address.getHostAddress() + '/' + mask;
    }
}
