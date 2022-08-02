package org.alliancegenome.agr_submission.util;
import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Compresses an InputStream in a memory-optimal, on-demand way only compressing enough to fill a buffer.
 * 
 * @author Ben La Monica
 */
public class GZIPCompressingInputStream extends InputStream {

    private InputStream in;
    private GZIPOutputStream gz;
    private OutputStream delegate;
    private byte[] buf = new byte[8192];
    private byte[] readBuf = new byte[8192];
    int read = 0;
    int write = 0;

    public GZIPCompressingInputStream(InputStream in) throws IOException {
        this.in = in;
        this.delegate = new OutputStream() {

            private void growBufferIfNeeded(int len) {
                if ((write + len) >= buf.length) {
                    // grow the array if we don't have enough space to fulfill the incoming data
                    byte[] newbuf = new byte[(buf.length + len) * 2];
                    System.arraycopy(buf, 0, newbuf, 0, buf.length);
                    buf = newbuf;
                }
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                growBufferIfNeeded(len);
                System.arraycopy(b, off, buf, write, len);
                write += len;
            }

            @Override
            public void write(int b) throws IOException {
                growBufferIfNeeded(1);
                buf[write++] = (byte) b;
            }
        };
        this.gz = new GZIPOutputStream(delegate); 
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        compressStream();
        int numBytes = Math.min(len, write-read);
        if (numBytes > 0) {
            System.arraycopy(buf, read, b, off, numBytes);
            read += numBytes;
        } else if (len > 0) {
            // if bytes were requested, but we have none, then we're at the end of the stream
            return -1;
        }
        return numBytes;
    }

    private void compressStream() throws IOException {
        // if the reader has caught up with the writer, then zero the positions out
        if (read == write) {
            read = 0;
            write = 0;
        }

        while (write == 0) {
            // feed the gzip stream data until it spits out a block
            int val = in.read(readBuf);
            if (val == -1) {
                // nothing left to do, we've hit the end of the stream. finalize and break out
                gz.close();
                break;
            } else if (val > 0) {
                gz.write(readBuf, 0, val);
            }
        }
    }

    @Override
    public int read() throws IOException {
        compressStream();
        if (write == 0) {
            // write should not be 0 if we were able to get data from compress stream, must mean we're at the end
            return -1;
        } else {
            // reading a single byte
            return buf[read++] & 0xFF;
        }
    }
}