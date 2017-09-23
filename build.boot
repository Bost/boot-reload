(set-env!
  :resource-paths #{"src"}
  :source-paths #{"test"}
  :dependencies
  '[[org.clojure/clojure "1.9.0-beta1" :scope "provided"]
    [http-kit "2.2.0" :scope "test"]
    [metosin/boot-alt-test "0.3.2" :scope "test"]])

(require '[metosin.boot-alt-test :refer [alt-test]])

(def +version+ "0.5.3")

(task-options!
  pom {:project     'adzerk/boot-reload
       :version     +version+
       :description "Boot task to auto-reload page resources in the browser."
       :url         "https://github.com/adzerk/boot-reload"
       :scm         {:url "https://github.com/adzerk/boot-reload"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build []
  (comp
   (pom)
   (jar)
   (install)))

(deftask run-tests []
  (comp
    (alt-test)))

(deftask dev []
  (comp
   (watch)
   (repl :server true)
   (run-tests)
   (build)
   (target)))

(deftask deploy []
  (comp
    (run-tests)
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
