package com.example.zhi.utils.updateUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 解析升级XML类
 * <p/>
 * Author: Eron
 * Date: 2016/4/7 0007
 * Time: 9:12
 */
public class ParseXmlService {

    public HashMap<String, String> parseXml(InputStream inputStream) throws Exception {

        HashMap<String, String> hashMap = new HashMap<>();
        // 实例化一个文件构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 通过文档构建器工厂获得一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 通过文档构建器构建一个文档实例
        Document document = builder.parse(inputStream);
        // 获得XML文件根节点
        Element root = document.getDocumentElement();
        // 获得所有子节点
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            // 遍历子节点
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                Element childElement = (Element) childNodes;
                // 版本号
                if ("version".equals(childElement.getNodeName())) {
                    hashMap.put("version", childElement.getFirstChild().getNodeValue());
                }
                // 软件名称
                else if (("name".equals(childElement.getNodeName()))) {
                    hashMap.put("name", childElement.getFirstChild().getNodeValue());
                }
                // 下载地址
                else if (("url".equals(childElement.getNodeName()))) {
                    hashMap.put("url", childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }
}
