(use 'clojure.java.io)

(defn extract-numbers [line]
  (->> (re-seq #"\d" line)))

(defn parse-first-and-last-number [numbers]
  (->> (str (or (first numbers) "0") (or (last numbers) "0"))
       (Integer/parseInt)))

(def str-number-mapping (hash-map "one" "1"
                                  "two" "2"
                                  "three" "3"
                                  "four" "4"
                                  "five" "5"
                                  "six" "6"
                                  "seven" "7"
                                  "eight" "8"
                                  "nine" "9"))

(defn find-overlapping-matches [regex-str input-str]
  (if (empty? input-str)
    '()
    (let [matches (re-seq (re-pattern regex-str) input-str)]
      (if (seq matches)
        (cons (first matches) (find-overlapping-matches regex-str (subs input-str 1)))
        (find-overlapping-matches regex-str (subs input-str 1))))))

(defn extract-numbers-in-written-form [line]
  (->> (find-overlapping-matches #"\d|one|two|three|four|five|six|seven|eight|nine" line) 
       (map #(get str-number-mapping % %))))

(defn part1 []
  (with-open [rdr (reader "day1/input.txt")]
    (let [numbers (map #(->> (extract-numbers %) 
                             (parse-first-and-last-number)) 
                       (line-seq rdr))] 
      (println "part 1 result: " (apply + numbers)))))
    

(defn part2 []
  (with-open [rdr (reader "day1/input.txt")]
    (let [numbers (map #(->> (extract-numbers-in-written-form %) 
                             (parse-first-and-last-number)) 
                       (line-seq rdr))]
      (println "part 2 result: " (apply + numbers)))))

(part1)
(part2)