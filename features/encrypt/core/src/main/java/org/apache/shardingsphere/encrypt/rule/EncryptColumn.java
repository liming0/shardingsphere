/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.encrypt.rule;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Encrypt column.
 */
@RequiredArgsConstructor
@Getter
public final class EncryptColumn {
    
    private final String cipherColumn;
    
    private final String assistedQueryColumn;
    
    private final String plainColumn;
    
    private final String likeQueryColumn;
    
    private final String encryptorName;
    
    private final String assistedQueryEncryptorName;
    
    private final String likeQueryEncryptorName;
    
    public EncryptColumn(final String cipherColumn, final String assistedQueryColumn, final String likeQueryColumn, final String plainColumn, final String encryptorName) {
        this(cipherColumn, assistedQueryColumn, plainColumn, likeQueryColumn, encryptorName, null, null);
    }
    
    /**
     * Get assisted query column.
     *
     * @return assisted query column
     */
    public Optional<String> getAssistedQueryColumn() {
        return Strings.isNullOrEmpty(assistedQueryColumn) ? Optional.empty() : Optional.of(assistedQueryColumn);
    }
    
    /**
     * Get like query column.
     *
     * @return like query column
     */
    public Optional<String> getLikeQueryColumn() {
        return Strings.isNullOrEmpty(likeQueryColumn) ? Optional.empty() : Optional.of(likeQueryColumn);
    }
    
    /**
     * Get plain column.
     *
     * @return plain column
     */
    public Optional<String> getPlainColumn() {
        return Strings.isNullOrEmpty(plainColumn) ? Optional.empty() : Optional.of(plainColumn);
    }
}
