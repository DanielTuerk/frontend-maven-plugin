package com.github.eirslett.maven.plugins.frontend.lib;

import java.io.File;

public interface NodeExecutorConfig {
  File getNodePath();
  File getNpmPath();
  File getInstallDirectory();
  File getWorkingDirectory();
  Platform getPlatform();
}

class InstallNodeExecutorConfig implements NodeExecutorConfig {

    private static final String NODE_WINDOWS = NodeInstaller.INSTALL_PATH.replaceAll("/", "\\\\") + "\\node.exe";
    private static final String NODE_DEFAULT = NodeInstaller.INSTALL_PATH + "/node";
    private static final String NPM = NodeInstaller.INSTALL_PATH + "/node_modules/npm/bin/npm-cli.js";

    private final InstallConfig installConfig;

    public InstallNodeExecutorConfig(InstallConfig installConfig) {
        this.installConfig = installConfig;
    }

    @Override
    public File getNodePath() {
        String nodeExecutable = getPlatform().isWindows() ? NODE_WINDOWS : NODE_DEFAULT;
        return new File(installConfig.getInstallDirectory() + nodeExecutable);
    }

    @Override
    public File getNpmPath() {
        return new File(installConfig.getInstallDirectory() + Utils.normalize(NPM));
    }

    @Override
    public File getInstallDirectory() {
        return installConfig.getInstallDirectory();
    }

    @Override
    public File getWorkingDirectory() {
        return installConfig.getWorkingDirectory();
    }

    @Override
    public Platform getPlatform() {
        return installConfig.getPlatform();
    }

    InstallConfig getInstallConfig() {
        return installConfig;
    }
}

/**
 * This {@link NodeExecutorConfig} provides the install path to the npm-cache command in the install folder.
 *
 * @author dtuerk
 */
final class InstallNodeNpmCacheExecutorConfig extends InstallNodeExecutorConfig {

    private static final String NPM_CACHE = "/bin/npm-cache";

    InstallNodeNpmCacheExecutorConfig(InstallConfig installConfig) {
        super(installConfig);
    }

    @Override
    public File getNpmPath() {
        return new File(getInstallConfig().getInstallDirectory() + Utils.normalize(NPM_CACHE));
    }
}