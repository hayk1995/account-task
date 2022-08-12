package com.example.concurent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class PreventDuplicateAspect {
    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final ConcurrentReferenceHashMap<String, Lock> lockMap = new ConcurrentReferenceHashMap<>();

    @Around("@annotation(com.example.concurent.PreventDuplicate)")
    public Object executeLocked(ProceedingJoinPoint pjp) throws Throwable {
        var args = pjp.getArgs();
        var names = ((MethodSignature) pjp.getSignature()).getParameterNames();
        var context = new StandardEvaluationContext();
        for (int i = 0; i < names.length; i++) {
            context.setVariable(names[i], args[i]);
        }

        PreventDuplicate annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(PreventDuplicate.class);
        String key = Objects.requireNonNull(parser.parseExpression(annotation.value()).getValue(context, String.class));
        Lock lock = getLock(key);
        try {
            lock.lock();
            return pjp.proceed();
        } finally {
            lock.unlock();
        }

    }

    private Lock getLock(String key) {
        return this.lockMap.compute(key, (k, v) -> v == null ? new ReentrantLock() : v);
    }

}
