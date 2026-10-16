package com.velters.gogoup;

import net.fabricmc.api.ModInitializer;

import java.io.IOException;

public final class GoGoUpMod implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            GoGoUpConfig.loadOnStartup();
        } catch (IOException exception) {
            throw new IllegalStateException("GoGoUp の設定を読み込めません。config/gogoup.json を確認してください。",
                    exception);
        }

        GoGoUpCommands.register();
    }
}
