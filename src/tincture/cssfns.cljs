(ns tincture.cssfns
  (:require-macros [garden.def :refer [defcssfn]])
  (:require
   [tincture.spec :refer [check-spec]]
   [cljs.spec.alpha :as s :include-macros true]))

(defcssfn
  ^{:doc "Create a linear gradient CSS function using two colors and two positions.

See [MDN linear-gradient
documentation](https://developer.mozilla.org/en-US/docs/Web/CSS/linear-gradient)
for posible values, I'm not gonna detail all the variants here.

**Args:**

Either two color stops, leaving the rest to default, or:

* dir (direction): See https://developer.mozilla.org/en-US/docs/Web/CSS/linear-gradient#Values

* color1: first color, all valid color-stops

* postition1: first position,
  either [percentage](https://developer.mozilla.org/en-US/docs/Web/CSS/percentage)
  or [length](https://developer.mozilla.org/en-US/docs/Web/CSS/length)

* color2: second color, all valid color stops

* postition2: second position,
  either [percentage](https://developer.mozilla.org/en-US/docs/Web/CSS/percentage)
  or [length](https://developer.mozilla.org/en-US/docs/Web/CSS/length)

**Example usage:**
```clojure
(garden.core/css [:.my-class {:background (linear-gradient \"to-left\" \"#333\" \"0%\" \"#eee\" \"100%\")}])
```
Result:
```css
.my-class {
  background: linear-gradient(to-left, #333 0%, #eee 100%);}
}
```
TODO Add more variants, multiple colors.

NOTE: No validation currently done on input.

NOTE: Your milage may vary on this one, there are simply too many
variations to account for all of them. Consider creating your own
custom css function using garden.
"}
  linear-gradient
  ([color1 color2]
   [color1 color2])
  ([dir color1 position1 color2 position2]
   [dir [color1 position1] [color2 position2]]))

(s/def ::percent
  (letfn [(pred [s]
            (re-matches #"^(\d\d?|100?)%$" (str s)))]
    (s/spec pred)))

(s/def ::color (s/or :percent ::percent :int (s/int-in 0 256)))
(def ^:private color-msg "Invalid color passed to rgb cssfn, 0-255 or percent 0%-100%")
(def ^:private alpha-msg "Invalid alpha value, has to be a float between 0 and 1 inclusive")

(s/def ::alpha
  (letfn [(pred [s]
            (and (float? s)
                 (<= s 1)
                 (>= s 0)))]
    (s/spec pred)))

(defcssfn
  ^{:doc "Create a RGB(A) color string by passing red green blue and optionally alpha.

**Args:**

* r (red): a number between 0 and 255 (inclusive) or a percent string (0% - 100%)
* g (green): a number between 0 and 255 (inclusive) or a percent string (0% - 100%)
* b (blue): a number between 0 and 255 (inclusive) or a percent string (0% - 100%)

**Optional:**

* a (alpha): floating point value between 0 and 1 inclusive

**Example usage:**
```clojure
(garden.core/css [:.my-class {:color (rgb 255 255 255 0.2)}])
```
Result:
```css
.my-class {
  color: rgb(255, 255, 255, 0.2);
}
```
"}
  rgb
  ([r g b]
   (let [r (check-spec r ::color color-msg)
         g (check-spec g ::color color-msg)
         b (check-spec b ::color color-msg)]
     [r g b]))
  ([r g b a]
   (let [r (check-spec r ::color color-msg)
         g (check-spec g ::color color-msg)
         b (check-spec b ::color color-msg)
         a (check-spec a ::alpha alpha-msg)
         ]
     [r g b a])))

(defcssfn
  ^{:doc "Create a CSS URL data type

Example usage:

```clojure
(garden.core/css [:.my-class {:background (url \"http://www.example.com/image.png\")}])
```
Result:
```css
.my-class {
  background: url(http://www.example.com/image.png);
}
```
NOTE: I make no attempt at validating URLs currently, unsure how
to do it properly
"}
  url
  )

(defcssfn
  ^{:doc "Create a calc CSS function, takes a variable number of arguments.

**Example:**
```clojure
(require '[garden.units :refer [percent px]])
(garden.core/css [:.my-class {:height (calc (percent 100) '- (px 20))}])
```
Result:
```css
.my-class {
  height: calc(100% - 20px);
}
```
NOTE: No validation is currently done on the input of this function
since it can be serveral different units, leaving the validation to
CSS.

NOTE: Due to how this functions arguments is layed out the calc call
takes its arguments using infix notation, which is not ideal but might
improve in future.
"
 }
  calc
  [& args]
  [args])
