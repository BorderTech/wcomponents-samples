package com.github.bordertech.wcomponents.task;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * WComponents requires a {@link Future} implementation that can be serializable to allow the user session to be
 * serialized.
 *
 * @author Jonathan Austin
 * @param <T> the future get result type
 * @since 1.0.0
 */
public interface TaskFuture<T> extends Future<T>, Serializable {

}
