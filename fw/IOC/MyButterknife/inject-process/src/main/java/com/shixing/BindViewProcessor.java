package com.shixing;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * Created by shixing on 2018/8/16.
 */

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {
    //get package's name
    private Elements elementUtils;
    //generate the java's file
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.elementUtils = processingEnvironment.getElementUtils();
        this.filer = processingEnvironment.getFiler();
    }

    /**
     *
     * @return the annotation's type that you want to it to be processed
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types=new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    /**
     *
     * @return latest jdk's version
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //key: Activity ,  value:all attributes that has "BindView" annotation in the currentActivity
        Map<TypeElement,List<FieldViewBinding>> targetMap=new HashMap<>();
        //when compiling，System.out.print() cannot be executed
        System.out.println("when compiling，System.out.print() cannot be executed");
        FileUtils.print("------------>    ");
        for(Element element:roundEnvironment.getElementsAnnotatedWith(BindView.class))
        {
            FileUtils.print("elment   "+element.getSimpleName().toString());
            TypeElement enClosingElement= (TypeElement) element.getEnclosingElement();
            // List<FieldViewBinding>   all attributes that have annotation in the current Activity
            List<FieldViewBinding> list=targetMap.get(enClosingElement);
            if(list==null)
            {
                list=new ArrayList<>();
                targetMap.put(enClosingElement,list);//
            }
            String packageName=getPackageName(enClosingElement);
            //get id
            int id=element.getAnnotation(BindView.class).value();
            String fieldName=element.getSimpleName().toString();
//           you can consider TypeMirror as  TextView
            TypeMirror typeMirror=element.asType();
            FieldViewBinding fieldViewBinding=new FieldViewBinding(fieldName,typeMirror,id);
            list.add(fieldViewBinding);
        }
        //TypeElement represent class type
        for(Map.Entry<TypeElement,List<FieldViewBinding>> item:targetMap.entrySet())
        {
            List<FieldViewBinding> list=item.getValue();

            if(list==null||list.size()==0)
            {
                continue;
            }
            //enClosingElement represent activity
            TypeElement enClosingElement=item.getKey();
//          get package's name
            String packageName=getPackageName(enClosingElement);
            //get string "MainActivity"
            String complite=getClassName(enClosingElement,packageName);
            //javaPoet's standard，MainActivity is  transformed as ClassName
            ClassName className=ClassName.bestGuess(complite);
//          ViewBinder type
            ClassName  viewBinder=ClassName.get("com.example.inject","ViewBinder");
//            begin generate java's file
            TypeSpec.Builder result=TypeSpec.classBuilder(complite+"$$ViewBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addTypeVariable(TypeVariableName.get("T",className))
                    .addSuperinterface(ParameterizedTypeName.get(viewBinder,className));
//          generate function's name
            MethodSpec.Builder methodBuilder=MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addAnnotation(Override.class)
                    .addParameter(className,"target",Modifier.FINAL);
//            constructor's logic,here is findViewById()
            for(int i=0;i<list.size();i++)
            {
                FieldViewBinding fieldViewBinding=list.get(i);
                //-->android.text.TextView
                String pacckageNameString=fieldViewBinding.getType().toString();
                ClassName viewClass=ClassName.bestGuess(pacckageNameString);
                //$L represent base type
                //$T represent class type
                methodBuilder.addStatement
                        ("target.$L=($T)target.findViewById($L)",fieldViewBinding.getName()
                                ,viewClass,fieldViewBinding.getResId());
            }

            result.addMethod(methodBuilder.build());

            try {
                //generate java's file
                JavaFile.builder(packageName,result.build())
                        .addFileComment("auto create make")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return false;
    }

    //
    //enClosingElement.getQualifiedName().toString() will return com.example.administrator.butterdepends.MainActivity
    private String getClassName(TypeElement enClosingElement, String packageName) {
        int packageLength=packageName.length()+1;
        return enClosingElement.getQualifiedName().toString().substring(packageLength).replace(".","$");
    }

    private String getPackageName(TypeElement enClosingElement) {
        return   elementUtils.getPackageOf(enClosingElement).getQualifiedName().toString();
    }
}
