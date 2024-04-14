package com.nurikov.tasklist.service;

import com.nurikov.tasklist.domain.task.TaskImage;

public interface ImageService {
    String upload(TaskImage taskImage);
}
