(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2"  :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "4.5.2")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/pixi
       :version     +version+
       :description "2D webGL renderer with canvas fallback"
       :url         "http://www.pixijs.com"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package []
  (comp
    (download :url (str "https://cdnjs.cloudflare.com/ajax/libs/pixi.js/" +lib-version+ "/pixi.min.js")
              :checksum "B716F1B41AE21FA51817E6CE0DCD2518")
    (download :url (str "https://cdnjs.cloudflare.com/ajax/libs/pixi.js/" +lib-version+ "/pixi.js")
              :checksum "D5E154816002E427627E1F28D7A42DFF")
    (sift :move {#"pixi\.js$" "cljsjs/pixi/development/pixi.inc.js"
                 #"pixi\.min\.js$" "cljsjs/pixi/development/pixi.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.pixi")
    (pom)
    (jar)))
