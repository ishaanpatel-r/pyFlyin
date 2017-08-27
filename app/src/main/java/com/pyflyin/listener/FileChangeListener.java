package com.pyflyin.listener;

public interface FileChangeListener {
    void onFileOpen();

    void onFileChanged(boolean save);

    void onFileSave();
}
