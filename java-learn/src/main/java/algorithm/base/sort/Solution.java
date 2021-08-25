package algorithm.base.sort;

import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

class Solution {

    public static void main(String[] args) throws IOException {

        System.out.println(frequencySort("Aacccbb"));
    }

    public static int countChar(String s, char c) {
        // 统计字符出现次数
        int count=0;
        for (int i=0, len=s.length(); i<len; i++) {
            if (c==s.charAt(i))
                count++;

        }
        return count;
    }
    public static int indexOf(char[] buff, int len, char c) {
        // 顺序检索字符位置，通过 len 限制终位
        for (int i=0; i<len; i++) {
            //if (''==buff[i]) // 此处需要调试一下空字符
            //    return -1;
            if (c==buff[i]) // 找到字符就返回对应位置
                return i;

        }
        return -1; // 未检索到

    }
    public static String frequencySort(String s) {
        char c; // 寻找字符
        char[] charBuff = new char[s.length()]; // 缓存已检索的字符
        int [] cntBuff  = new int[s.length()];  // 缓存字符出现的次数
        char[] result   = new char[s.length()]; // 结果缓存
        int    buffSize = 0; // 缓存长度
        int    resSize  = 0; // 结果长度
        // 循环 String 中的字符，获取对应的字符和出现次数
        for(int i=0, len=s.length();i<len; i++) {
            // 外循环
            c = s.charAt(i);
            if (indexOf(charBuff, buffSize, c)==-1) {
                // 如果未检测到字符，就进行记录和计数
                charBuff[buffSize] = c; // 缓存已找到的字符
                cntBuff[buffSize]  = countChar(s, c); // 统计字符出现次数
                buffSize++; // 缓存长度自增1
            }

        }

        // 根据缓存长度，按字符出现次数，从大到小先进行排序，需要根据字符出现次数大小来交换字符位置
        for(int i=0; i<buffSize; i++) {
            boolean flag = false;
            for(int j=0; j<buffSize-i; j++) { // 每循环一次，可以确定最右侧是有序的
                if (cntBuff[i] < cntBuff[j+i]) {
                    // 如果左侧小于右侧，将左右侧位置进行交换，确保最右侧是最小值
                    int temp = cntBuff[j+i]; // 先交换出现次数
                    cntBuff[j+i] = cntBuff[i];
                    cntBuff[i] = temp;
                    char tempChar = charBuff[j+i]; // 再交换字符位置
                    charBuff[j+i] = charBuff[i];
                    charBuff[i] = tempChar;
                    flag = true; // 表示可以向右找到比自己大的位置

                }

            }
            if (!flag) { // 如果向右找不到次数比当前大的位置，则退出循环
                break;
            }
        }
        // 按排序顺序的结果，按顺序，以字符出现次数生成新的字符数组
        for (int i=0; i<buffSize; i++) {
            c = charBuff[i];  // 取出字符
            int  cnt = cntBuff[i]; // 取现次数
            // 通过出现次数循环向result写入字符
            for (int j=0; j<cnt; j++) {
                result[resSize]=c;
                resSize++;
            }
        }
        // 组装成 String 返回结果
        return new String(result);

    }

}
