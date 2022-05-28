/*
 * @Date: 2022-05-19 09:09:26
 * @LastEditors: Junxi ZHANG
 * @LastEditTime: 2022-05-19 09:25:47
 * @FilePath: /app-jee8/src/main/java/fr/cours/conf/JAXRSConfiguration.java
 */
package fr.cours.conf;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
}