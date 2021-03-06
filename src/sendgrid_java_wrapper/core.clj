(ns sendgrid-java-wrapper.core
  (:import (com.sendgrid SendGrid
                         SendGrid$Email
                         SendGrid$Response)))

(defn- prepare-email [from subject html]
  (-> (SendGrid$Email.)
     (.setFrom from)
     (.setSubject subject)
     (.setHtml html)))

(defn- -send [{username :api_user password :api_key} email]
  (.send (SendGrid. username password)
         email))

(defn send-email
  [auth {to :to from :from subject :subject html :html from-name :from-name}]
  (let [email (-> (prepare-email from subject html)
                  (.addTo to)
                  (.setFromName from-name))
        response (-send auth email)]
    (.getMessage response)))

(defn send-email-groupid
  [auth {to :to from :from subject :subject html :html from-name :from-name group-id :group-id}]
  (let [email (-> (prepare-email from subject html)
                  (.addTo to)
                  (.setFromName from-name)
                  (.setASMGroupId group-id))
        response (-send auth email)]
    (.getMessage response)))

(defn bulk-email
  [auth {bcc :bcc from :from  subject :subject html :html from-name :from-name}]
  (let [email (-> (prepare-email from subject html)
                  (.setFromName from-name)
                  (.setBcc (into-array String bcc)))
        response (-send auth email)]
    (.getMessage response)))

(defn bulk-email-groupid
  [auth {bcc :bcc from :from  subject :subject html :html from-name :from-name group-id :group-id}]
  (let [email (-> (prepare-email from subject html)
                  (.setFromName from-name)
                  (.setASMGroupId group-id)
                  (.setBcc (into-array String bcc)))
        response (-send auth email)]
    (.getMessage response)))
