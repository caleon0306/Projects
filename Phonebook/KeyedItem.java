package assg9_leonc22;

public abstract class KeyedItem<KT extends Comparable<? super KT>> {

   private KT searchKey;

   public KeyedItem(KT key) {
       searchKey = key;
   }

   public KT getKey() {
       return searchKey;
   }
}