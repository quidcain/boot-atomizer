(set-env!
  :resource-patsh #{"src"}
  :dependencies '[
                  [org.clojure/clojure "1.8.0" :scope "provided"]
                  [adzerk/bootlaces "0.1.13" :scope "test"]])

(require  '[adzerk.bootlaces :refer :all]
          '[boot.git :refer [last-commit]])

;build-jar push-release

(def +version+ "0.1.0")
(bootlaces! +version+)

(task-options!
  pom {:project 'zaeny/boot-atomizer
       :version +version+
       :description "Boot task to compile css classes using Atomizer "
       :url "https://github.com/azizzaeny/boot-atomizer"
       :scm {:url "https://github/azizzaeny/boot-atomizer"}
       :license {"MIT License" "https://opensource.org/licenses/MIT"}}

  push {:repo           "deploy"
        :ensure-branch  "master"
        :ensure-clean   true
        :ensure-tag     (last-commit)
        :ensure-version +version+})

(deftask deploy
  "Builds uberjar, installs it to local Maven repo, and deploys it to Clojars."
  []
  (comp (build-jar) (push-release)))

; (deftask build []
;   (comp
;    (pom)
;    (jar)
;    (install)))
;
; (deftask dev []
;   (comp
;     (watch)
;     (build)
;     (repl :server true))))

; (deftask push-release []
;   (comp
;    (build)
;    (push :repo "clojars")))