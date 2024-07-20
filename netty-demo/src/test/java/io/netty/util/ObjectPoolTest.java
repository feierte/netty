package io.netty.util;

import io.netty.channel.ChannelOutboundBuffer;
import io.netty.util.internal.ObjectPool;
import io.netty.util.internal.UnstableApi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Jie Zhao
 * @date 2024/7/17 21:03
 */
@Slf4j
public class ObjectPoolTest {

    @Test
    public void test() {
        User user = User.of("张三", 18);
        log.info("user: {}", user);
    }

    @Data
    static class User {
        static ObjectPool<User> RECYCLER = ObjectPool.newPool(new ObjectPool.ObjectCreator<User>() {
            @Override
            public User newObject(ObjectPool.Handle<User> handle) {
                return new User(handle);
            }
        });
        private String name;
        private int age;
        private final Recycler.EnhancedHandle<User> handle;

        public User(ObjectPool.Handle<User> handle) {
            this.handle = (Recycler.EnhancedHandle<User>) handle;
        }

        public static User of(String name, int age) {
            User user = RECYCLER.get();
            user.name = name;
            user.age = age;
            return user;
        }
    }

    @UnstableApi
    public abstract static class EnhancedHandle<T> implements Recycler.Handle<T> {

        public abstract void unguardedRecycle(Object object);

        private EnhancedHandle() {
        }
    }


}
