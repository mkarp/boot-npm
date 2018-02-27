(set-env!
 :dependencies  '[[org.clojure/clojure "1.8.0"]
                  [boot/core           "2.7.2"]
                  [cheshire            "5.7.1"]
                  [degree9/boot-semver "1.7.0" :scope "test"]
                  [mkarp/degree9-boot-io "1.3.3"]
                  [mkarp/degree9-boot-exec "1.1.2"]]
 :resource-paths   #{"src"}
 :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"
                                    :username (System/getenv "CLOJARS_USER")
                                    :password (System/getenv "CLOJARS_PASS")}]))

(require
  '[degree9.boot-npm :refer :all]
 '[degree9.boot-semver :refer :all])

(task-options!
  target {:dir #{"target"}}
  pom {:project 'mkarp/degree9-boot-npm
       :description "boot-clj task for wrapping npm"
       :url         "https://github.com/mkarp/degree9-boot-npm"
       :scm         {:url "https://github.com/mkarp/degree9-boot-npm"}})

(deftask develop
  "Build boot-npm for development."
  []
  (comp
   (version :develop true
            :minor 'inc
            :patch 'zero
            :pre-release 'snapshot)
   (watch)
   (target)
   (build-jar)))

(deftask deploy
  "Build boot-npm and deploy to clojars."
  []
  (comp
   (version)
   (target)
   (build-jar)
   (push-release)))
