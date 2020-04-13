package com.lisz;

import javassist.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class Test2 {
    public static void main(String[] args) throws Exception {
        updateMethod();
    }

    public static void updateMethod(){
        try {
            ClassPool classPool = new ClassPool(true);
            //如果该文件引入了其它类，需要利用类似如下方式声明
            //cPool.importPackage("java.util.List");

            //设置class文件的位置
            classPool.insertClassPath("/Users/shuzheng/IdeaProjects/ASM/target/classes");

            //获取该class对象
            CtClass cClass = classPool.get("com.lisz.AdditionCalculator");

            //获取到对应的方法
            CtMethod cMethod = cClass.getDeclaredMethod("add");

            //更改该方法的内部实现
            //需要注意的是对于参数的引用要以$开始，不能直接输入参数名称
            cMethod.setBody("{ return $1*$1*$1+$2*$2*$2; }");

            //替换原有的文件
            cClass.writeFile("/Users/shuzheng/IdeaProjects/ASM/target/classes");

            System.out.println("\"" + cMethod.getName() + "\"" + " method has been hacked!!");
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
