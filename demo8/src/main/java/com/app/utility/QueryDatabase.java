/* QueryDatabase.java
 *
 * 
 */

package com.app.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class QueryDatabase {

	private static final Logger LOGGER = Logger.getLogger(QueryDatabase.class);

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		EntityManagerFactory emf;
		EntityManager em;
		EntityTransaction et;
		Query query;
		List<Object> result;
		int affected;

		InputStreamReader stream;
		BufferedReader reader;
		String line;

		emf = Persistence.createEntityManagerFactory("demo");
		em = emf.createEntityManager();
		et = em.getTransaction();

		stream = new InputStreamReader(System.in);
		reader = new BufferedReader(stream);

		line = reader.readLine();
		if (line != null) {
			while (line != null && !line.equals("quit")) {
				try {
					if (!line.isEmpty()) {
						if (line.equals("begin")) {
							et.begin();
						} else if (line.equals("commit")) {
							et.commit();
						} else if (line.equals("rollback")) {
							et.rollback();
						} else {
							query = em.createQuery(line);
							if (line.startsWith("update")
									|| line.startsWith("delete")) {
								affected = query.executeUpdate();
								LOGGER.debug(String.format(
										"%d objects affected", affected));
							} else {
								result = query.getResultList();
								LOGGER.debug(String.format(
										"%d results found", result.size()));
								for (Object obj : result) {
									if (!(obj instanceof Object[])) {
										LOGGER.debug(obj);
									} else {
										for (Object subObj : (Object[]) obj) {
											LOGGER.debug(subObj);
											LOGGER.debug("");
										}
										LOGGER.debug("");
									}
								}
							}
						}
					}
				} catch (Exception oops) {
					LOGGER.error(oops.getMessage(),oops);
				}
				line = reader.readLine();
			}

		}
	}
}
