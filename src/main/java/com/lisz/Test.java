package com.lisz;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class Test {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(Hello.class.getClassLoader().getResourceAsStream("com/lisz/Hello.class"));
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(ASM4, mv) {
                    @Override
                    public void visitCode() {
                        //visitMethodInsn(INVOKESTATIC, "com/lisz/HelloProxy", "after", "()V", false);
                        visitMethodInsn(INVOKESTATIC, "com/lisz/HelloProxy", "before", "()V", false);
                        super.visitCode();
                    }
                };
            }
        };

        cr.accept(cv, 0);
        byte[] b2 = cw.toByteArray();

        MyClassLoader cl = new MyClassLoader();
        cl.loadClass("com.lisz.HelloProxy");
        Class c2 = cl.defineClass("com.lisz.Hello", b2);
        c2.getConstructor().newInstance();

        String path = (String)System.getProperty("user.dir");
        File f = new File(path + "src/main/java/com/lisz/");
        f.mkdirs();

        FileOutputStream fos = new FileOutputStream(new File(path + "/src/main/java/com/lisz/Hello_0.class"));
        fos.write(b2);
        fos.flush();
        fos.close();
    }
}
